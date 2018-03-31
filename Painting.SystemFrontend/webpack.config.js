const webpack = require('webpack');
const path = require('path');
const UglifyJSPlugin = require('uglifyjs-webpack-plugin');

// variables
const isProduction = process.argv.indexOf('-p') >= 0;
const sourcePath = path.join(__dirname, './src');
const outPath = path.join(__dirname, './dist');
const moment = require("moment");

// plugins
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const tsImportPluginFactory = require('ts-import-plugin');
const buildTime = moment();


const basePlugins = [
    new HtmlWebpackPlugin({
        template: './assets/index.html',
        favicon: './assets/logo.png',
        filename: 'index.html'
    }),
    new HtmlWebpackPlugin({
        template: './assets/404.html',
        favicon: './assets/logo.png',
        filename: '404.html'
    }),
    new webpack.DefinePlugin({
        FRONT_END_BUILD: JSON.stringify(buildTime.format("YYYYMMDD")),
        FRONT_END_BUILD_TIME: JSON.stringify(buildTime.format())
    }),
    new MiniCssExtractPlugin({
        // Options similar to the same options in webpackOptions.output
        // both options are optional
        filename: "[name].css",
        chunkFilename: "[id].css"
    })

];

const devPlugins = [
    new webpack.DefinePlugin({
        APIROOTURL: JSON.stringify("http://localhost:8080/")
    }),

];

const prodPlugins = [
    new webpack.DefinePlugin({
        APIROOTURL: JSON.stringify("http://localhost:8080/"),
    }),

    new UglifyJSPlugin(),

];

const plugins = basePlugins.concat(isProduction ? prodPlugins : devPlugins);

module.exports = {
    mode: isProduction ? "production" : "development",
    context: sourcePath,
    entry: {
        main: './index.ts'
    },
    output: {
        path: outPath,
        filename: 'index.[hash:5].js',
        chunkFilename: '[name].[hash:5].js',
        publicPath: '/'
    },
    target: 'web',

    resolve: {
        extensions: ['.js', '.ts', '.tsx', 'json'],
        // Fix webpack's default behavior to not load packages with jsnext:main module
        // (jsnext:main directs not usually distributable es6 format, but es6 sources)
        mainFields: ['module', 'browser', 'main'],
        alias: {
            'app': path.resolve(__dirname, 'src/app/')
        }
    },
    plugins: plugins,
    module: {
        rules: [
            // .ts, .tsx
            {
                test: /\.tsx?$/,
                use: [{
                    loader: "ts-loader",
                    options: {
                        getCustomTransformers: () => ({
                            before: [tsImportPluginFactory({
                                libraryName: 'antd',
                                libraryDirectory: 'lib',
                                style: true
                            })]
                        }),

                    },
                }]
            },
            // css
            {
                test: /\.(css|less)$/,
                use: [
                    MiniCssExtractPlugin.loader,
                    {
                        loader: 'css-loader',
                        query: {
                            modules: true,
                            sourceMap: !isProduction,
                            importLoaders: 1,
                            localIdentName: '[local]',
                        }
                    },
                    {
                        loader: "less-loader", // compiles Less to CSS
                        options: {
                            javascriptEnabled: true
                        }
                    }]
            },
            // static assets
            {test: /\.html$/, use: 'html-loader'},
            {test: /\.png$/, use: 'url-loader?limit=10000'},
            {test: /\.jpg$/, use: 'file-loader'},
            {test: /\.md$/, use: 'raw-loader'},
        ]
    },
    devtool: isProduction ? false : 'cheap-module-eval-source-map',
    optimization: {
        splitChunks: {
            name: true,
            cacheGroups: {
                commons: {
                    chunks: 'initial',
                    minChunks: 2
                },
                vendors: {
                    test: /[\\/]node_modules[\\/]/,
                    chunks: 'all',
                    priority: -10
                }
            }
        },
        runtimeChunk: true
    },
    devServer: {
        contentBase: sourcePath,
        hot: true,
        inline: true,
        historyApiFallback: {
            disableDotRule: true
        },
        stats: 'minimal',
        open: true,
        port: 3000
    },

};

deploy_path="/var/www/"
        if [ ! -d "$deploy_path" ]
        then
                project_path="http://114.215.188.21/161250010_Trapx00/Tagx00_Phase_I.git"
                git clone $project_path $deploy_path
        else
                cd $deploy_path
                git pull
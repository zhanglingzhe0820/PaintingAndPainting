import React from "react";
import { NormalPageConfig, RouteConfig } from "./RouteConfig";


export const doWorkPage: RouteConfig = new NormalPageConfig({
  path: "/missions/:missionId/doWork",
  render: async (props) => {
    const DoWorkPage = (await import("../../pages/DoWorkPage")).DoWorkPage;
    return <DoWorkPage missionId={props.match.params.missionId} readonly={false}/>;
  },
});

export const seeResultPage: RouteConfig = new NormalPageConfig({
  path: "/missions/:missionId/result",
  render: async (props) => {
    const Page = (await import("../../pages/SeeResultPage")).SeeResultPage;
    return <Page missionId={props.match.params.missionId}/>;
  },
});

export const missionsPage: RouteConfig = new NormalPageConfig({
  path: "/missions",
  render: async (props) => {
    const Page = (await import("../../pages/MissionsPage")).MissionsPage;
    return <Page/>;
  },
});

export const homePage: RouteConfig = new NormalPageConfig({
  path: "/",
  render: async (props) => {
    const HomePage = (await import("../../pages/HomePage")).HomePage;
    return <HomePage/>;
  },
});

export const browsePage: RouteConfig = new NormalPageConfig({
  path: "/browse",
  render: async (props) => {
    const BrowsePage = (await import("../../pages/BrowsePage")).BrowsePage;
    return <BrowsePage/>;
  },
});

export const registerPage: RouteConfig = new NormalPageConfig( {
  path: "/register",
  render: async (props) => {
    const RegisterPage = (await import("../../pages/RegisterPage")).RegisterPage;
    return <RegisterPage/>;
  },
});

export const aboutPage: RouteConfig = new NormalPageConfig({
  path: "/about",
  render: async (props) => {
    const AboutPage = (await import("../../pages/AboutPage")).AboutPage;
    return <AboutPage/>;
  },
});





export default [
  missionsPage,
  seeResultPage,
  doWorkPage,
  browsePage,
  registerPage,
  aboutPage
]

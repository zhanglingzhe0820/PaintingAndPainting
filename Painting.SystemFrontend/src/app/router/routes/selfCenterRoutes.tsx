import React from "react";
import { NormalPageConfig, RedirectConfig, RouteConfig } from "./RouteConfig";

export const dashboardPage: RouteConfig = new NormalPageConfig({
  path: "/self/dashboard",
  render: async (props) => {
    const Page = (await import("../../pages/SelfPage/DashboardPage")).DashboardPage;
    return <Page/>;
  },
});

export const achievementPage: RouteConfig = new NormalPageConfig({
  path: "/self/achievement",
  render: async (props) => {
    const Page = (await import("../../pages/SelfPage/AchievementPage")).AchievementPage;
    return <Page/>;
  },
});

export const personalInfoPage: RouteConfig = new NormalPageConfig({
  path: "/self/personalInfo",
  render: async (props) => {
    const Page = (await import("../../pages/SelfPage/PersonalInfoPage")).PersonalInfoPage;
    return <Page/>;
  },
});


export const selfRedirect: RouteConfig = new RedirectConfig({
  path: "/self",
  redirectTo: "/self/dashboard"
});

export default [
  dashboardPage,
  achievementPage,
  personalInfoPage,
  selfRedirect,
]

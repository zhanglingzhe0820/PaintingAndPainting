import React from "react"
import { NormalPageConfig, RouteConfig } from "./RouteConfig";

export const notFoundPage: RouteConfig = new NormalPageConfig({
  path: "",
  render: async (props) => {
    const NotFoundPage = (await import("../../pages/NotFound")).NotFoundPage;
    return <NotFoundPage/>
  },
  exact: false
});

import React from "react";
import rootRoutes from './routes/rootRoutes';
import selfCenterRoutes from './routes/selfCenterRoutes';
import { notFoundPage } from "./routes/notFoundRoute";

export default [
  ...selfCenterRoutes,
  ...rootRoutes,
  notFoundPage
]

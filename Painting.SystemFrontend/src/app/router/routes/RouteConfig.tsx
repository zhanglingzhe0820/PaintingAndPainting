import { Redirect, Route, RouteComponentProps } from "react-router";
import { AsyncComponent } from "../AsyncComponent";
import React from 'react';

export abstract class RouteConfig {
  path: string = "";
  exact: boolean = true;
  abstract construct();

}

export class NormalPageConfig extends RouteConfig {

  render: (props: RouteComponentProps<any>) => Promise<JSX.Element> = () => new Promise(resolve => resolve());


  constructor(config: Partial<NormalPageConfig>) {
    super();
    Object.assign(this, config);
  }

  construct() {
    return <Route
      exact={this.exact}
      key={this.path}
      path={this.path}
      render={props => <AsyncComponent render={this.render} props={props}/>}/>
  }
}

export class RedirectConfig extends RouteConfig {

  redirectTo: string = "/notfound";

  constructor(config: Partial<RedirectConfig>) {
    super();
    Object.assign(this, config);
  }

  construct() {
    return <Redirect
      key={this.path}
      path={this.path}
      to={this.redirectTo}
      />;
  }

}

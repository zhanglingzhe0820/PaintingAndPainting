import { BaseService, NetworkResponse } from "./BaseService";
import { HttpMethod } from "./utils";

export interface LoginResult {
  token: string,
  jwtRoles: { authority: string }[];
  email: string;
}

function encryptPassword(password: string) {
  return password;
}

export class UserService extends BaseService {
  constructor() {
    super("account");
  }

  async login(username: string, password: string): Promise<NetworkResponse<LoginResult>> {
    password = encryptPassword(password);

    // return new NetworkResponse(200, {
    //     token: "123",
    //     jwtRoles: ["ROLE_WORKER"],
    //     email: "1@1.com"
    //   }
    // );
    //
    return await this.fetch({
      route: "login",
      queryParams: {username, password}
    });
  }

  async register(username: string, password: string) {
    password = encryptPassword(password);

    return await this.fetch({
      route: "register",
      body: {username, password},
      method: HttpMethod.POST
    });
  }

}

export enum UserRole {
  ROLE_REQUESTER = "ROLE_REQUESTER",
  ROLE_WORKER = "ROLE_WORKER",
  ROLE_ADMIN = "ROLE_ADMIN"
}


export class User {
  public username: string;
  public role: UserRole;
  public token: string;

  constructor(params: Partial<User>) {
    Object.assign(this, params);
  }

}

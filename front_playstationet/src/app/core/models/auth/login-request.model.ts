import { Role } from "../../enums/role.enum";

export interface LoginRequest {
  username: string;
  password: string;
  role: Role;
}

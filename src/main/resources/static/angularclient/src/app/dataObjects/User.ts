export class User {
  id!: bigint;
  name!: string;
  email!: string;
  password!: string;
  username!: string;
  admin: boolean = false;
}

export interface Horse {
  id?: number;
  name: string;
  description: string;
  birthdate: Date;
  gender: string;
  owner: number;
  mom?: number;
  dad?: number;
}

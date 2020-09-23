import { Moment } from 'moment';

export interface IPunch {
  id?: number;
  user?: string;
  mylatitude?: number;
  myLongitude?: number;
  punchtime?: Moment;
}

export class Punch implements IPunch {
  constructor(
    public id?: number,
    public user?: string,
    public mylatitude?: number,
    public myLongitude?: number,
    public punchtime?: Moment
  ) {}
}

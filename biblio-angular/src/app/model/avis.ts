import { Livre } from './livre';

export interface Avis {
  id: number;
  note: number;
  commentaire: string;
  date: Date;
  livre: Livre;
}

export interface AvisCreateUpdate {
  id: number;
  note: number;
  commentaire: string;
  date: Date;
  livre: number;
}

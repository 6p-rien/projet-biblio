import { Auteur } from './auteur';
import { Collec } from './collec';
import { Editeur } from './editeur';
import { Genre } from './genre';

export interface Livre {
  id?: number;
  titre: string;
  resume: string | null;
  annee: number;
  auteur: Auteur;
  editeur: Editeur;
  collection: Collec | null;
  genres: Array<Genre>;
}



export interface LivreOnlyId {
  titre: string;
  resume: string | null;
  annee: number;
  auteurId: number;
  editeurId: number;
  collectionId: number | null;
  genreIds: Array<number>;
}



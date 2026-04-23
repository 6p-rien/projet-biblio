import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  LivreOnlyId,
  Livre,
} from '../model/livre';

@Injectable({
  providedIn: 'root',
})
export class LivreService {
  private readonly apiUrl = '/api/livre';

  constructor (private http: HttpClient){ }

  public findAll(): Observable<Livre[]>{
    return this.http.get<Livre[]>(this.apiUrl);
  }

  public add(livre: Livre): Observable<LivreOnlyId> {
    return this.http.post<LivreOnlyId>(this.apiUrl, this.toRequest(livre));
  }

  public deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  public update(id: number, livre: Livre): Observable<LivreOnlyId> {
    return this.http.put<LivreOnlyId>(`${this.apiUrl}/${id}`, this.toRequest(livre));
  }

  private toRequest(livre: Livre): LivreOnlyId {
    return {
      titre: livre.titre,
      resume: livre.resume,
      annee: livre.annee,
      auteur: livre.auteur.id ,
      editeur: livre.editeur.id,
      collec: livre.collec ? livre.collec.id : null,
      genres: (livre.genres ?? []).map((genre) => (genre.id )),
    };
  }
}

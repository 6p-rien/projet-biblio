import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Editeur } from '../model/editeur';

@Injectable({
  providedIn: 'root',
})
export class EditeurService {
  constructor(private http: HttpClient) { }

  public findAll(): Observable<Editeur[]> {
    return this.http.get<Editeur[]>("/api/editeur");
  }

  public add(editeur: Editeur): Observable<Editeur> {
    return this.http.post<Editeur>("/api/editeur", editeur);
  }

  public update(id: number, editeur: Editeur): Observable<Editeur> {
    return this.http.put<Editeur>(`/api/editeur/${id}`, editeur);
  }

  public deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`/api/editeur/${id}`);
  }
}

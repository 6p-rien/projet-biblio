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
  private readonly apiUrl = '/livre';

  constructor (private http: HttpClient){ }

  public findAll(): Observable<Livre[]>{
    return this.http.get<Livre[]>(this.apiUrl);
  }

  public add(livreOnlyId: LivreOnlyId): Observable<LivreOnlyId> {
    return this.http.post<LivreOnlyId>(this.apiUrl, livreOnlyId);
  }

  public deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  public update(id: number, livreOnlyId: LivreOnlyId): Observable<LivreOnlyId> {
    return this.http.put<LivreOnlyId>(`${this.apiUrl}/${id}`, livreOnlyId);
  }

}

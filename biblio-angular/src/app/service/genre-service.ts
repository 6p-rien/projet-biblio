import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Genre } from '../model/genre';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class GenreService {
  constructor(private http: HttpClient) { }

  public findAll(): Observable<Genre[]> {
    return this.http.get<Genre[]>("/collec");
  }

  public update(id: number, libelle: string): Observable<Genre> {
    const genre: Genre = { id, libelle };
    return this.http.put<Genre>(`/collec/${ id }`, genre);
  }

  public add(genre: Genre): Observable<Genre> {
    return this.http.post<Genre>("/collec", genre);
  }

  public deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`/collec/${ id }`);
  }
}

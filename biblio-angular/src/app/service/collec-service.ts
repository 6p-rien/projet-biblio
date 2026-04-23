import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Collec } from '../model/collec';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CollecService {
  constructor(private http: HttpClient) { }

  public findAll(): Observable<Collec[]> {
    return this.http.get<Collec[]>("/collec");
  }

  public update(id: number, collec: Collec): Observable<Collec> {
    return this.http.put<Collec>(`/collec/${ id }`, collec);
  }

  public add(collec: Collec): Observable<Collec> {
    return this.http.post<Collec>("/collec", collec);
  }

  public deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`/collec/${ id }`);
  }
}

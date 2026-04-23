import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Avis, AvisCreateUpdate } from './model/avis';

@Injectable({
  providedIn: 'root',
})
export class AvisService {
  private http = inject(HttpClient);

  public findAll(): Observable<Avis[]> {
    return this.http.get<Avis[]>('/avis');
  }

  public add(avis: AvisCreateUpdate): Observable<AvisCreateUpdate> {
    return this.http.post<AvisCreateUpdate>('/avis', avis);
  }
  public update(avis: AvisCreateUpdate): Observable<AvisCreateUpdate> {
    return this.http.put<AvisCreateUpdate>(`/avis/${avis.id}`, avis);
  }
  public deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`/matiere/${id}`);
  }
}

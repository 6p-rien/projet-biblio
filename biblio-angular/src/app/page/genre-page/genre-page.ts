import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { Genre } from '../../model/genre';
import { GenreService } from '../../service/genre';

@Component({
  selector: 'app-editeur',
  imports: [ CommonModule, ReactiveFormsModule ],
  templateUrl: './editeur.html',
  styleUrl: './editeur.css',
})
export class EditeurPage implements OnInit {

  private titleService = inject(Title);
  private genreService = inject(GenreService);
  private formBuilder = inject(FormBuilder);

  protected genres$!: Observable<Genre[]>;
  private refresh$ = new Subject<void>();

  protected formEditeur!: FormGroup;
  protected formLibelleCtrl!: FormControl;

  ngOnInit(): void {
    this.titleService.setTitle("Liste des genres");

    this.genres$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.genreService.findAll())
    );

    // Création des contrôles
    this.formLibelleCtrl = this.formBuilder.control("", Validators.required);

    this.formEditeur = this.formBuilder.group({
      libelle: this.formLibelleCtrl,
    });
  }

  private reload() {
    this.refresh$.next();
  }

  public addEditeur() {
    const genre: Genre = {
      id: 0,
      libelle: this.formLibelleCtrl.value,
    };

    this.genreService.add(genre).subscribe(() => this.reload());
  }

  public deletegenre(genre: Genre) {
    this.genreService.deleteById(genre.id).subscribe(() => this.reload());
  }
}

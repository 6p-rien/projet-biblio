import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { Observable, startWith, Subject, switchMap, tap } from 'rxjs';
import { Auteur } from '../../model/auteur';
import { Collec } from '../../model/collec';
import { Editeur } from '../../model/editeur';
import { Genre } from '../../model/genre';
import { Livre, LivreOnlyId } from '../../model/livre';
import { AuteurService } from '../../service/auteur-service';
import { CollecService } from '../../service/collec-service';
import { EditeurService } from '../../service/editeur';
import { GenreService } from '../../service/genre-service';
import { LivreService } from '../../service/livre-service';

@Component({
  selector: 'app-livre-page',
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './livre-page.html',
  styleUrl: './livre-page.css',
})
export class LivrePage implements OnInit {
  private titleService: Title = inject(Title);
  private livreService: LivreService = inject(LivreService);
  private auteurService: AuteurService = inject(AuteurService);
  private editeurService: EditeurService = inject(EditeurService);
  private collecService: CollecService = inject(CollecService);
  private genreService: GenreService = inject(GenreService);
  private formBuilder: FormBuilder = inject(FormBuilder);

  protected livres$!: Observable<Livre[]>;
  protected editGenresIds: Record<number, number[]> = {};
  protected auteurs$!: Observable<Auteur[]>;
  protected editeurs$!: Observable<Editeur[]>;
  protected collecs$!: Observable<Collec[]>;
  protected genres$!: Observable<Genre[]>;
  private refresh$: Subject<void> = new Subject<void>();

  protected formLivre!: FormGroup;
  protected formTitreCtrl!: FormControl;
  protected formResumeCtrl!: FormControl;
  protected formAnneeCtrl!: FormControl;
  protected formAuteurIdCtrl!: FormControl;
  protected formEditeurIdCtrl!: FormControl;
  protected formCollecIdCtrl!: FormControl;
  protected formGenresIdsCtrl!: FormControl;

  ngOnInit(): void {
    this.titleService.setTitle('Liste des livres');

    this.livres$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.livreService.findAll()),
      tap((livres) => {
        livres.forEach((l) => {
          if (l.id !== undefined) {
            this.editGenresIds[l.id] = l.genres.map((g) => g.id);
          }
        });
      }),
    );

    this.auteurs$ = this.auteurService.findAll();
    this.editeurs$ = this.editeurService.findAll();
    this.collecs$ = this.collecService.findAll();
    this.genres$ = this.genreService.findAll();

    this.formTitreCtrl = this.formBuilder.control('', Validators.required);
    this.formResumeCtrl = this.formBuilder.control('');
    this.formAnneeCtrl = this.formBuilder.control(new Date().getFullYear(), [Validators.required, Validators.min(0)]);
    this.formAuteurIdCtrl = this.formBuilder.control(0, [Validators.required, Validators.min(1)]);
    this.formEditeurIdCtrl = this.formBuilder.control(0, [Validators.required, Validators.min(1)]);
    this.formCollecIdCtrl = this.formBuilder.control<number | null>(null);
    this.formGenresIdsCtrl = this.formBuilder.control<number[]>([]);

    this.formLivre = this.formBuilder.group({
      titre: this.formTitreCtrl,
      resume: this.formResumeCtrl,
      annee: this.formAnneeCtrl,
      auteurId: this.formAuteurIdCtrl,
      editeurId: this.formEditeurIdCtrl,
      collecId: this.formCollecIdCtrl,
      genresIds: this.formGenresIdsCtrl,
    });
  }

  private reload(): void {
    this.refresh$.next();
  }

  private buildLivreOnlyId(): LivreOnlyId {
    const collecIdValue = this.formCollecIdCtrl.value;
    const genreIds = (this.formGenresIdsCtrl.value ?? [])
      .map((v: number) => Number(v))
      .filter((v: number) => Number.isInteger(v) && v > 0);

    return {
      titre: String(this.formTitreCtrl.value ?? '').trim(),
      resume: String(this.formResumeCtrl.value ?? '').trim() || null,
      annee: Number(this.formAnneeCtrl.value),
      auteurId: Number(this.formAuteurIdCtrl.value),
      editeurId: Number(this.formEditeurIdCtrl.value),
      collectionId: collecIdValue ? Number(collecIdValue) : null,
      genreIds: genreIds,
    };
  }

  public addLivre(): void {
    this.livreService.add(this.buildLivreOnlyId()).subscribe(() => {
      this.formLivre.reset({
        titre: '',
        resume: '',
        annee: new Date().getFullYear(),
        auteurId: 0,
        editeurId: 0,
        collecId: null,
        genresIds: [],
      });
      this.reload();
    });
  }

  public updateLivre(
    livre: Livre,
    titre: string,
    resume: string,
    anneeValue: string,
    auteurIdValue: string,
    editeurIdValue: string,
    collecIdValue: string,
    genreIds: number[],
  ): void {
    if (!livre.id) {
      return;
    }

    const annee = Number(anneeValue);
    const auteurId = Number(auteurIdValue);
    const editeurId = Number(editeurIdValue);
    const collectionId = collecIdValue ? Number(collecIdValue) : null;

    const livreOnlyId: LivreOnlyId = {
      titre: titre.trim(),
      resume: resume.trim() || null,
      annee: Number.isInteger(annee) && annee >= 0 ? annee : livre.annee,
      auteurId: auteurId > 0 ? auteurId : livre.auteur.id,
      editeurId: editeurId > 0 ? editeurId : livre.editeur.id,
      collectionId: collectionId,
      genreIds: genreIds,
    };

    this.livreService.update(livre.id, livreOnlyId).subscribe(() => this.reload());
  }

  public deleteLivre(livre: Livre): void {
    if (livre.id) {
      this.livreService.deleteById(livre.id).subscribe(() => this.reload());
    }
  }
}

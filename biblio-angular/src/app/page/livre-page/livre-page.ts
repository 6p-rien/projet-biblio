import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { Livre, LivreOnlyId } from '../../model/livre';
import { LivreService } from '../../service/livre-service';

@Component({
  selector: 'app-livre-page',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './livre-page.html',
  styleUrl: './livre-page.css',
})
export class LivrePage implements OnInit {
  private titleService: Title = inject(Title);
  private livreService: LivreService = inject(LivreService);
  private formBuilder: FormBuilder = inject(FormBuilder);

  protected livres$!: Observable<Livre[]>;
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
    );

    this.formTitreCtrl = this.formBuilder.control('', Validators.required);
    this.formResumeCtrl = this.formBuilder.control('');
    this.formAnneeCtrl = this.formBuilder.control(new Date().getFullYear(), [Validators.required, Validators.min(0)]);
    this.formAuteurIdCtrl = this.formBuilder.control(0, [Validators.required, Validators.min(1)]);
    this.formEditeurIdCtrl = this.formBuilder.control(0, [Validators.required, Validators.min(1)]);
    this.formCollecIdCtrl = this.formBuilder.control<number | null>(null);
    this.formGenresIdsCtrl = this.formBuilder.control('');

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
    const genreIds = String(this.formGenresIdsCtrl.value ?? '')
      .split(',')
      .map((v) => Number(v.trim()))
      .filter((v) => Number.isInteger(v) && v > 0);

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
        genresIds: '',
      });
      this.reload();
    });
  }

  public updateLivre(livre: Livre): void {
    if (livre.id) {
      const livreOnlyId: LivreOnlyId = {
        titre: livre.titre,
        resume: livre.resume,
        annee: livre.annee,
        auteurId: livre.auteur.id,
        editeurId: livre.editeur.id,
        collectionId: livre.collection ? livre.collection.id : null,
        genreIds: livre.genres.map((g) => g.id),
      };
      this.livreService.update(livre.id, livreOnlyId).subscribe(() => this.reload());
    }
  }

  public deleteLivre(livre: Livre): void {
    if (livre.id) {
      this.livreService.deleteById(livre.id).subscribe(() => this.reload());
    }
  }
}

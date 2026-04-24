import { Component, inject, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { AvisService } from '../../service/avis-service';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { Avis, AvisCreateUpdate } from '../../model/avis';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-avis-page',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './avis-page.html',
  styleUrl: './avis-page.css',
})
export class AvisPage implements OnInit {
  private titleService: Title = inject(Title);
  private avisService: AvisService = inject(AvisService);
  private formBuilder: FormBuilder = inject(FormBuilder);

  protected avis$!: Observable<Avis[]>;
  private refresh$: Subject<void> = new Subject<void>();

  protected formAvis!: FormGroup;
  protected formNoteCtrl!: FormControl;
  protected formCommentaireCtrl!: FormControl;
  protected formDateCtrl!: FormControl;
  protected formLivreCtrl!: FormControl;

  protected isEditOpen: boolean = false;
  protected formEditAvis!: FormGroup;
  protected formEditNoteCtrl!: FormControl;
  protected formEditCommentaireCtrl!: FormControl;
  protected formEditDateCtrl!: FormControl;
  protected formEditLivreCtrl!: FormControl;
  protected editAvisId!: number;

  ngOnInit(): void {
    this.titleService.setTitle('Avis');
    this.avis$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.avisService.findAll()),
    );

    this.formNoteCtrl = this.formBuilder.control('', Validators.required);
    this.formCommentaireCtrl = this.formBuilder.control('', Validators.required);
    this.formDateCtrl = this.formBuilder.control('', Validators.required);
    this.formLivreCtrl = this.formBuilder.control('', Validators.required);

    this.formAvis = this.formBuilder.group({
      note: this.formNoteCtrl,
      commentaire: this.formCommentaireCtrl,
      date: this.formDateCtrl,
      livre: this.formLivreCtrl,
    });

    this.formEditNoteCtrl = this.formBuilder.control('', Validators.required);
    this.formEditCommentaireCtrl = this.formBuilder.control('', Validators.required);
    this.formEditDateCtrl = this.formBuilder.control('', Validators.required);
    this.formEditLivreCtrl = this.formBuilder.control('', Validators.required);

    this.formEditAvis = this.formBuilder.group({
      note: this.formEditNoteCtrl,
      commentaire: this.formEditCommentaireCtrl,
      date: this.formEditDateCtrl,
      livre: this.formEditLivreCtrl,
    });
  }
  public addAvis() {
    const avis: AvisCreateUpdate = {
      id: 0,
      commentaire: this.formCommentaireCtrl.value,
      note: this.formNoteCtrl.value,
      date: this.formDateCtrl.value,
      idLivre: this.formLivreCtrl.value,
    };
    this.avisService.add(avis).subscribe(() => this.reload());
  }
  private reload() {
    this.refresh$.next();
  }

  public deleteAvis(avis: Avis) {
    this.avisService.deleteById(avis.id).subscribe(() => this.reload());
  }

  public openEditModal(avis: Avis) {
    this.isEditOpen = true;
    this.editAvisId = avis.id;

    this.formEditAvis.patchValue({
      note: avis.note,
      commentaire: avis.commentaire,
      date: avis.date,
      livre: avis.livre,
    });
  }

  public updateAvis() {
    const avis: AvisCreateUpdate = {
      id: this.editAvisId,
      note: this.formEditNoteCtrl.value,
      commentaire: this.formEditCommentaireCtrl.value,
      date: this.formEditDateCtrl.value,
      idLivre: this.formEditLivreCtrl.value,
    };
    this.avisService.update(avis).subscribe(() => this.reload());
    this.isEditOpen = false;
  }

  public closeEditModal() {
    this.isEditOpen = false;
  }
}

import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { Editeur } from '../../model/editeur';
import { EditeurService } from '../../service/editeur';

@Component({
  selector: 'app-editeur',
  imports: [ CommonModule, ReactiveFormsModule ],
  templateUrl: './editeur.html',
  styleUrl: './editeur.css',
})
export class EditeurPage implements OnInit {

  private titleService = inject(Title);
  private editeurService = inject(EditeurService);
  private formBuilder = inject(FormBuilder);

  protected editeurs$!: Observable<Editeur[]>;
  private refresh$ = new Subject<void>();

  protected formEditeur!: FormGroup;
  protected formNomCtrl!: FormControl;
  protected formPaysCtrl!: FormControl;

  ngOnInit(): void {
    this.titleService.setTitle("Liste des éditeurs");

    this.editeurs$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.editeurService.findAll())
    );

    // Création des contrôles
    this.formNomCtrl = this.formBuilder.control("", Validators.required);
    this.formPaysCtrl = this.formBuilder.control("");

    this.formEditeur = this.formBuilder.group({
      nom: this.formNomCtrl,
      pays: this.formPaysCtrl
    });
  }

  private reload() {
    this.refresh$.next();
  }

  public addEditeur() {
    const editeur: Editeur = {
      id: 0,
      nom: this.formNomCtrl.value,
      pays: this.formPaysCtrl.value
    };

    this.editeurService.add(editeur).subscribe(() => this.reload());
  }

  public deleteEditeur(editeur: Editeur) {
    this.editeurService.deleteById(editeur.id).subscribe(() => this.reload());
  }
}

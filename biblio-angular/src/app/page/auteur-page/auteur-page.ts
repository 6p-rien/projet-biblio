import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { Auteur } from '../../model/auteur';
import { AuteurService } from '../../service/auteur-service';

@Component({
  selector: 'app-auteur-page',
  imports: [ CommonModule, ReactiveFormsModule ],
  templateUrl: './auteur-page.html',
  styleUrl: './auteur-page.css',
})
export class AuteurPage implements OnInit {
  private titleService: Title = inject(Title);
  private auteurService: AuteurService = inject(AuteurService);
  private formBuilder: FormBuilder = inject(FormBuilder);

  protected auteurs$!: Observable<Auteur[]>;
  private refresh$: Subject<void> = new Subject<void>();

  protected formAuteur!: FormGroup;
  protected formNomCtrl!: FormControl;
  protected formPrenomCtrl!: FormControl;
  protected formNationaliteCtrl!: FormControl;

  ngOnInit(): void {
    this.titleService.setTitle('Liste des auteurs');

    // FindAll avec rechargement automatique
    this.auteurs$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.auteurService.findAll()),
    );

    this.formNomCtrl = this.formBuilder.control('', Validators.required);
    this.formPrenomCtrl = this.formBuilder.control('', Validators.required);
    this.formNationaliteCtrl = this.formBuilder.control('', Validators.required);

    this.formAuteur = this.formBuilder.group({
      nom: this.formNomCtrl,
      prenom: this.formPrenomCtrl,
      nationalite: this.formNationaliteCtrl,
    });
  }

  private reload() {
    this.refresh$.next();
  }

  public addAuteur() {
    const auteur: Auteur = {
      id: 0,
      nom: this.formNomCtrl.value,
      prenom: this.formPrenomCtrl.value,
      nationalite: this.formNationaliteCtrl.value,
    };

    this.auteurService.add(auteur).subscribe(() => this.reload());
  }

  public updateAuteur(auteur: Auteur) {
    this.auteurService.update(auteur.id, auteur).subscribe(() => this.reload());
  }

  public deleteAuteur(auteur: Auteur) {
    this.auteurService.deleteById(auteur.id).subscribe(() => this.reload());
  }
}

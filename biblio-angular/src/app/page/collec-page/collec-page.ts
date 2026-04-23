import { Component, inject } from '@angular/core';
import { CollecService } from '../../service/collec-service';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Collec } from '../../model/collec';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-collec-page',
  imports: [ CommonModule, ReactiveFormsModule ],
  templateUrl: './collec-page.html',
  styleUrl: './collec-page.css',
})
export class CollecPage {
  private collecService: CollecService = inject(CollecService);
  private formBuilder: FormBuilder = inject(FormBuilder);

  protected collec$!: Observable<Collec[]>;
  private refresh$: Subject<void> = new Subject<void>();

  protected formCollec!: FormGroup;
  protected formNomCtrl!: FormControl;

  protected updateId: number | null = null;

  ngOnInit(): void {
    
    this.collec$ = this.refresh$.pipe(
      startWith(0), // Initialisation => forcer le chargement une première fois
      switchMap(() => this.collecService.findAll()) // Transformer au moment du next()
    );

    // Fabrication du formulaire avec le FormBuilder
    this.formNomCtrl = this.formBuilder.control("", Validators.required);

    this.formCollec = this.formBuilder.group({
      // Description des contrôles du formulaire
      nom: this.formNomCtrl
    });
  }

  private reload() {
    this.refresh$.next();
  }

  public addCollec() {
    const collec: Collec = {
      id: 0,
      nom: this.formNomCtrl.value
    };

    this.collecService.add(collec).subscribe(() => this.reload());
  }

  public updateCollec(id: number, nom: string) {
    this.collecService.update(id, nom).subscribe(() => {
      this.updateId = id;
      this.reload();
    });
  }

  public deleteCollec(collec: Collec) {
    this.collecService.deleteById(collec.id).subscribe(() => this.reload());
  }
}

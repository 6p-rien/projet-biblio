import { Component, inject, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { AvisService } from '../../avis-service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { Avis } from '../../model/avis';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-avis-page',
  imports: [CommonModule],
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

  ngOnInit(): void {
    this.titleService.setTitle('Avis');
    this.avis$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.avisService.findAll()),
    );

    this.formNoteCtrl = this.formBuilder.control('', Validators.required);

    this.formAvis = this.formBuilder.group({
      note: this.formNoteCtrl,
    });
  }

  private reload() {
    this.refresh$.next();
  }
}

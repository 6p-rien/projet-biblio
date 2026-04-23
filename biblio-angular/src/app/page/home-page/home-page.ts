import { Component, inject } from '@angular/core';
import { CollecService } from '../../service/collec-service';

@Component({
  selector: 'app-home-page',
  imports: [],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage {
  private collecService: CollecService = inject(CollecService);
}

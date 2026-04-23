import { Routes } from '@angular/router';
import { CollecPage } from './page/collec-page/collec-page';
import { HomePage } from './page/home-page/home-page';
import { AuteurPage } from './page/auteur-page/auteur-page';

export const routes: Routes = [
  { path: 'home', component: HomePage },
  { path: 'auteur', component: AuteurPage },
  { path: 'collec', component: CollecPage },

  { path: '', redirectTo: 'home', pathMatch: 'full' },
];

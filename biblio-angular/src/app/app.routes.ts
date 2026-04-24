import { Routes } from '@angular/router';
import { CollecPage } from './page/collec-page/collec-page';
import { HomePage } from './page/home-page/home-page';
import { AuteurPage } from './page/auteur-page/auteur-page';
import { EditeurPage } from './page/editeur/editeur';
import { LivrePage } from './page/livre-page/livre-page';
import { GenrePage } from './page/genre-page/genre-page';
import { AvisPage } from './page/avis-page/avis-page';
import { authGuard } from './guard/auth-guard';
import { LoginPage } from './page/login-page/login-page';

export const routes: Routes = [
  { path: 'home', component: HomePage, title: 'Home Page', canActivate: [ authGuard ]},
  { path: 'auteur', component: AuteurPage, title: 'Liste des Auteurs', canActivate: [ authGuard ] },
  { path: 'collec', component: CollecPage, title: 'Liste des collections', canActivate: [ authGuard ] },
  { path: 'editeur', component: EditeurPage, title: 'Liste des éditeurs' , canActivate: [ authGuard ]},
  { path: 'livre', component: LivrePage, title: 'Liste des livres', canActivate: [ authGuard ] },
  { path: 'genre', component: GenrePage, title: 'Liste des genres', canActivate: [ authGuard ] },
  { path: 'avis', component: AvisPage , canActivate: [ authGuard ]},
  { path: 'login', component: LoginPage },


  { path: '', redirectTo: 'home', pathMatch: 'full' },
];

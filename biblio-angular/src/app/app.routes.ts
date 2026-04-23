import { Routes } from '@angular/router';
import { CollecPage } from './page/collec-page/collec-page';
import { HomePage } from './page/home-page/home-page';
import { AuteurPage } from './page/auteur-page/auteur-page';
import { EditeurPage } from './page/editeur/editeur';
import { LivrePage } from './page/livre-page/livre-page';

export const routes: Routes = [
    { path: 'home', component: HomePage, title: 'Home Page' },
    { path: 'auteur', component: AuteurPage, title: 'Liste des Auteurs' },
    { path: 'collec', component: CollecPage, title: 'Liste des collections' },
    { path: 'editeur', component: EditeurPage, title: 'Liste des éditeurs' },
    { path: 'livre', component: LivrePage, title:'Liste des livres'},

    { path: '', redirectTo: 'home', pathMatch: 'full' }
];

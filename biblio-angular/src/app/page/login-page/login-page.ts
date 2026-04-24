import { Component, inject } from '@angular/core';
import { AuthRequest } from '../../dto/auth-request';
import { AuthService } from '../../service/auth-service';
import { AbstractControl, FormBuilder, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  imports: [ ReactiveFormsModule ],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
})
export class LoginPage {
  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);
  private formBuilder: FormBuilder = inject(FormBuilder);



  protected formAuth = this.formBuilder.group({
    username: [ '', [ Validators.required ] ],
    password: [ '', [ Validators.required ] ],
  });


  public connexion() {
    if (this.formAuth.invalid) {
      this.formAuth.markAllAsTouched();
      return;
    }

    const authRequest: AuthRequest = this.formAuth.getRawValue() as AuthRequest;

    this.authService.auth(authRequest).subscribe(resp => {
      if (resp.success) {
        this.authService.token = resp.token;
        this.router.navigate([ '/home' ]);
      }
    });
  }
}

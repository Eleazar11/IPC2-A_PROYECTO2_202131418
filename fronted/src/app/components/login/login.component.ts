import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginData = {
    email: '',
    password: ''
  };

  constructor(private http: HttpClient) {}

  onLogin() {
    this.http.post('http://localhost:8080/api/login', this.loginData)
      .subscribe(
        response => {
          console.log('Inicio de sesión exitoso', response);
        },
        error => {
          console.error('Error al iniciar sesión', error);
        }
      );
  }
}


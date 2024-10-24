import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Importa FormsModule

@Component({
  selector: 'app-registro',
  standalone: true, // Es un componente independiente
  imports: [CommonModule, FormsModule], // Agrega FormsModule aquí
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent {
  user = {
    username: '',
    password: ''
  };
}

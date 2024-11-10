import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { FormsModule } from '@angular/forms'; 
import { UsuariosService } from '../../services/usuarios/usuarios.service';
import { Router } from '@angular/router';
import { Usuario } from '../../entidades/Usuario';
import { TipoUsuario } from '../../entidades/TipoUsuario';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent {
  
  mensaje: string | null = null; // Propiedad para mostrar un mensaje

  // Constructor con la inyección de dependencias
  constructor(private usuariosService: UsuariosService, private router: Router) {}

  // Método para mostrar un mensaje al hacer clic en el botón
  mostrarMensaje() {
    this.mensaje = "Le diste click al botón de registrar.";
    console.log('Usuario registrado:');
    setTimeout(() => this.mensaje = null, 3000); // El mensaje desaparece después de 3 segundos
  }

  // Método para manejar el envío del formulario
  onRegister(form: any) {
    if (form.valid) {
      // Obtener datos del formulario
      const formValue = form.value;

      // Mapear los datos del formulario al objeto Usuario
      const usuario: Usuario = {
        nombreUsuario: formValue.nombreUsuario,
        contrasena: formValue.contrasena,
        rol: TipoUsuario[formValue.tipoUsuario as keyof typeof TipoUsuario], // Convertir el tipoUsuario a TipoUsuario
        fotoPerfil: formValue.fotoPerfil ? formValue.fotoPerfil.files[0] : null, // Para archivo
        hobbies: formValue.hobbies || '', // Valor predeterminado si está vacío
        temasInteres: formValue.temasInteres || '',
        descripcion: formValue.descripcion || '',
        gustos: formValue.gustos || '',
        fechaCreacion: new Date() // o la fecha que prefieras
      };

      // Llamar al servicio para registrar el usuario
      this.usuariosService.registrarUsuario(usuario).subscribe(response => {
        console.log('Usuario registrado:', response);
        // Redirigir a la vista principal después de un registro exitoso
        this.router.navigate(['/home']);
      }, error => {
        console.error('Error al registrar el usuario:', error);
      });

    } else {
      console.log('El formulario es inválido');
    }
  }
}

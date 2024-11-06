import { TipoUsuario } from "./TipoUsuario";

export interface Usuario {
    nombreUsuario: string;
    contrasena: string;
    rol: TipoUsuario;
    fotoPerfil: any;
    hobbies: string;
    temasInteres: string;
    descripcion: string;
    gustos: string;
    fechaCreacion: Date;
}

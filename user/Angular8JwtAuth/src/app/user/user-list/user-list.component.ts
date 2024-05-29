import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../_services/auth.service'; // Importez votre service d'authentification
import { User } from '../user.model';
import { UserService } from '../../_services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  public users: User[] = [];
  public isAdmin: boolean = false;

  constructor(
    private userService: UserService,
    private authService: AuthService, // Injectez votre service d'authentification
    private router: Router
  ) {}

  ngOnInit(): void {
    this.isAdmin = this.authService.hasRole('ROLE_ADMIN'); // Vérifiez si l'utilisateur a le rôle admin
    this.reloadData();
  }

  reloadData() {
    this.userService.getUsers().subscribe(data => {
      this.users = data;
      console.log(data);
    });
  }

  updateUser(id: string | undefined) {
    if (id) {
      this.router.navigate(['UpdateUserComponent', id]);
    }
  }

  deleteUser(id: string | undefined) {
    if (id) {
      this.userService.deleteUser(id).subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error)
      );
    }
  }
}

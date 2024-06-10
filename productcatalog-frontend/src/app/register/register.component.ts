import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { UserModel } from '../models/user.mode';
import { UserCreateModel } from '../models/user-create.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  formRegister: FormGroup = new FormGroup({});

  constructor(
    private formBuilder: FormBuilder,
    private service: AuthService
  ) {}

  ngOnInit(): void {
    this.formRegister = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required]
    })
  }

  public submit(formRegister: FormGroup): void {
    if(formRegister.valid) {
      this.service.create(new UserCreateModel(
        formRegister.controls['username'].value,
        formRegister.controls['email'].value,
        formRegister.controls['password'].value,
        "USER"
      )).subscribe(res => {
        console.log(res)
      })
    }
  }

}

import { Component, OnInit } from '@angular/core';
import {Owner} from '../../dto/owner';
import {OwnerService} from '../../service/owner.service';

@Component({
  selector: 'app-owner',
  templateUrl: './owner.component.html',
  styleUrls: ['./owner.component.scss']
})
export class OwnerComponent implements OnInit {
  owners: Owner[];

  constructor(
    private service: OwnerService,
  ) { }

  ngOnInit(): void {
    this.reloadOwners();
  }

  reloadOwners() {
    this.service.getAll().subscribe({
      next: data => {
        console.log('received horses', data);
        this.owners = data;
      }
    });
  }

}

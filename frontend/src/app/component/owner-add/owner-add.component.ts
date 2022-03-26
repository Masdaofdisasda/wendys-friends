import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Owner} from '../../dto/owner';
import {OwnerService} from '../../service/owner.service';

@Component({
  selector: 'app-owner-add',
  templateUrl: './owner-add.component.html',
  styleUrls: ['./owner-add.component.scss']
})
export class OwnerAddComponent implements OnInit {
  @Output() reload = new EventEmitter();
  active = false;
  owners: Owner[];

  constructor(
    private service: OwnerService,
    ) { }

  ngOnInit(): void {
  }

  add(givenname: string, surname: string, email: string): void {
    this.service.addOwner({givenname, surname, email} as Owner)
      .subscribe(horse => {this.owners.push(horse);
        this.reload.emit();});
    this.active=false;
  }
}

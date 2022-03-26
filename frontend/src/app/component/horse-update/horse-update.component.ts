import {Component, Input, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {HorseService} from '../../service/horse.service';
import {Observable} from 'rxjs';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {Owner} from '../../dto/owner';
import {OwnerService} from '../../service/owner.service';

@Component({
  selector: 'app-horse-update',
  templateUrl: './horse-update.component.html',
  styleUrls: ['./horse-update.component.scss']
})
export class HorseUpdateComponent implements OnInit {
  @Input() horse: Horse;
  horses: Horse[];
  success = false;
  selectedOwner?: Owner;
  selectedMom?: Horse;
  selectedDad?: Horse;
  mom?: Horse;
  dad?: Horse;

  constructor(
    private service: HorseService,
    private ownerService: OwnerService,
  ) { }

  ngOnInit(): void {
    if (this.horse.mom) {
      this.service.getHorse(this.horse.mom).subscribe(mom => this.mom = mom);
    }
    if (this.horse.dad) {
      this.service.getHorse(this.horse.dad).subscribe(dad => this.dad = dad);
    }
  }

  update(id: number, name: string, description: string, birthdate: Date, gender: string, owner: number, mom: number, dad: number): void {
    this.service.updateHorses({id, name, description, birthdate, gender, owner, mom, dad} as Horse)
      .subscribe(horse => {this.horses.push(horse);});
    this.horse=null;
    this.success = true;
    this.selectedMom=null;
    this.selectedDad=null;
  }

  searchOwner = (text$: Observable<string>) => text$.pipe(
    debounceTime(200),
    distinctUntilChanged(),
    switchMap((searchText)=> this.ownerService.ownerLookup(searchText))
  );

  searchMom = (text$: Observable<string>) => text$.pipe(
    debounceTime(200),
    distinctUntilChanged(),
    // switchMap allows returning an observable rather than maps array
    switchMap( (searchText) =>  this.service.horseLookupMom(searchText) )
  );

  searchDad = (text$: Observable<string>) => text$.pipe(
    debounceTime(200),
    distinctUntilChanged(),
    // switchMap allows returning an observable rather than maps array
    switchMap( (searchText) =>  this.service.horseLookupDad(searchText) )
  );

  /**
   * Used to format the result data from the lookup into the
   * display and list values. Maps `{name: "band", id:"id" }` into a string
   */
  resultFormatHorseValue(value: any) {
    return value.name;
  }

  /**
   * Used to format the result data from the lookup into the
   * display and list values. Maps `{name: "band", id:"id" }` into a string
   */
  resultFormatOwnerValue(value: any) {
    return value.givenname +' '+ value.surname;
  }

  /**
   * Initially binds the string value and then after selecting
   * an item by checking either for string or key/value object.
   */
  inputFormatHorse(value: any)   {
    if(value.name)
    { return value.name;}
    return value;
  }
  /**
   * Initially binds the string value and then after selecting
   * an item by checking either for string or key/value object.
   */
  inputFormatOwner(value: any)   {
    if(value.surname)
    { return value.givenname +' '+ value.surname;}
    return value;
  }
}

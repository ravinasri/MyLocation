import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPunch } from 'app/shared/model/punch.model';

@Component({
  selector: 'jhi-punch-detail',
  templateUrl: './punch-detail.component.html',
})
export class PunchDetailComponent implements OnInit {
  punch: IPunch | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ punch }) => (this.punch = punch));
  }

  previousState(): void {
    window.history.back();
  }
}

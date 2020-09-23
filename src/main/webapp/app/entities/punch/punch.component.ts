import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPunch } from 'app/shared/model/punch.model';
import { PunchService } from './punch.service';
import { PunchDeleteDialogComponent } from './punch-delete-dialog.component';

@Component({
  selector: 'jhi-punch',
  templateUrl: './punch.component.html',
})
export class PunchComponent implements OnInit, OnDestroy {
  punches?: IPunch[];
  eventSubscriber?: Subscription;

  constructor(protected punchService: PunchService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.punchService.query().subscribe((res: HttpResponse<IPunch[]>) => (this.punches = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPunches();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPunch): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPunches(): void {
    this.eventSubscriber = this.eventManager.subscribe('punchListModification', () => this.loadAll());
  }

  delete(punch: IPunch): void {
    const modalRef = this.modalService.open(PunchDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.punch = punch;
  }
}

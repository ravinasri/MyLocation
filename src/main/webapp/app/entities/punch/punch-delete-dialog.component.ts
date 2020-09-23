import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPunch } from 'app/shared/model/punch.model';
import { PunchService } from './punch.service';

@Component({
  templateUrl: './punch-delete-dialog.component.html',
})
export class PunchDeleteDialogComponent {
  punch?: IPunch;

  constructor(protected punchService: PunchService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.punchService.delete(id).subscribe(() => {
      this.eventManager.broadcast('punchListModification');
      this.activeModal.close();
    });
  }
}

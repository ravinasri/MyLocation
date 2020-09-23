import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPunch, Punch } from 'app/shared/model/punch.model';
import { PunchService } from './punch.service';

@Component({
  selector: 'jhi-punch-update',
  templateUrl: './punch-update.component.html',
})
export class PunchUpdateComponent implements OnInit {
  isSaving = false;
  punchtimeDp: any;

  editForm = this.fb.group({
    id: [],
    user: [],
    mylatitude: [],
    myLongitude: [],
    punchtime: [],
  });

  constructor(protected punchService: PunchService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ punch }) => {
      this.updateForm(punch);
    });
  }

  updateForm(punch: IPunch): void {
    this.editForm.patchValue({
      id: punch.id,
      user: punch.user,
      mylatitude: punch.mylatitude,
      myLongitude: punch.myLongitude,
      punchtime: punch.punchtime,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const punch = this.createFromForm();
    if (punch.id !== undefined) {
      this.subscribeToSaveResponse(this.punchService.update(punch));
    } else {
      this.subscribeToSaveResponse(this.punchService.create(punch));
    }
  }

  private createFromForm(): IPunch {
    return {
      ...new Punch(),
      id: this.editForm.get(['id'])!.value,
      user: this.editForm.get(['user'])!.value,
      mylatitude: this.editForm.get(['mylatitude'])!.value,
      myLongitude: this.editForm.get(['myLongitude'])!.value,
      punchtime: this.editForm.get(['punchtime'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPunch>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}

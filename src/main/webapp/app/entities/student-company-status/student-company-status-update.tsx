import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStudentProfile } from 'app/shared/model/student-profile.model';
import { getEntities as getStudentProfiles } from 'app/entities/student-profile/student-profile.reducer';
import { ICompanyProfile } from 'app/shared/model/company-profile.model';
import { getEntities as getCompanyProfiles } from 'app/entities/company-profile/company-profile.reducer';
import { getEntity, updateEntity, createEntity, reset } from './student-company-status.reducer';
import { IStudentCompanyStatus } from 'app/shared/model/student-company-status.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StudentCompanyStatusUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const studentProfiles = useAppSelector(state => state.studentProfile.entities);
  const companyProfiles = useAppSelector(state => state.companyProfile.entities);
  const studentCompanyStatusEntity = useAppSelector(state => state.studentCompanyStatus.entity);
  const loading = useAppSelector(state => state.studentCompanyStatus.loading);
  const updating = useAppSelector(state => state.studentCompanyStatus.updating);
  const updateSuccess = useAppSelector(state => state.studentCompanyStatus.updateSuccess);
  const handleClose = () => {
    props.history.push('/student-company-status' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getStudentProfiles({}));
    dispatch(getCompanyProfiles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...studentCompanyStatusEntity,
      ...values,
      student: studentProfiles.find(it => it.id.toString() === values.student.toString()),
      company: companyProfiles.find(it => it.id.toString() === values.company.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...studentCompanyStatusEntity,
          student: studentCompanyStatusEntity?.student?.id,
          company: studentCompanyStatusEntity?.company?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="placementPortalApp.studentCompanyStatus.home.createOrEditLabel" data-cy="StudentCompanyStatusCreateUpdateHeading">
            <Translate contentKey="placementPortalApp.studentCompanyStatus.home.createOrEditLabel">
              Create or edit a StudentCompanyStatus
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="student-company-status-id"
                  label={translate('placementPortalApp.studentCompanyStatus.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('placementPortalApp.studentCompanyStatus.ctc')}
                id="student-company-status-ctc"
                name="ctc"
                data-cy="ctc"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.studentCompanyStatus.location')}
                id="student-company-status-location"
                name="location"
                data-cy="location"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 600, message: translate('entity.validation.maxlength', { max: 600 }) },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.studentCompanyStatus.status')}
                id="student-company-status-status"
                name="status"
                data-cy="status"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="student-company-status-student"
                name="student"
                data-cy="student"
                label={translate('placementPortalApp.studentCompanyStatus.student')}
                type="select"
              >
                <option value="" key="0" />
                {studentProfiles
                  ? studentProfiles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.studentID}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="student-company-status-company"
                name="company"
                data-cy="company"
                label={translate('placementPortalApp.studentCompanyStatus.company')}
                type="select"
              >
                <option value="" key="0" />
                {companyProfiles
                  ? companyProfiles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/student-company-status" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default StudentCompanyStatusUpdate;

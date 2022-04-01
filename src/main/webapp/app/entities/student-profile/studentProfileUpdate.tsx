import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUser } from 'app/shared/model/user.model';
import { getUser, getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset, createStudentProfile } from './student-profile.reducer';
import { IStudentProfile } from 'app/shared/model/student-profile.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const studentProfileUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  // const studentProfileEntity = useAppSelector(state => state.studentProfile.entity);
  const studentProfileEntity2 = useAppSelector(state => state.studentProfile.entity);
  const loading = useAppSelector(state => state.studentProfile.loading);
  const updating = useAppSelector(state => state.studentProfile.updating);
  const updateSuccess = useAppSelector(state => state.studentProfile.updateSuccess);
  const handleClose = () => {
    props.history.push('/student-profile' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    // const entity = {
    //   ...studentProfileEntity2,
    //   ...values,
    //   user: users.find(it => it.id.toString() === values.user.toString()),
    // };
    const entity = { ...values };

    if (isNew) {
      dispatch(createStudentProfile(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...studentProfileEntity2,
          user: studentProfileEntity2?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="placementPortalApp.studentProfile.home.createOrEditLabel" data-cy="StudentProfileCreateUpdateHeading">
            <Translate contentKey="placementPortalApp.studentProfile.home.createOrEditLabel">Create or edit a StudentProfile</Translate>
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
                  id="student-profile-id"
                  label={translate('placementPortalApp.studentProfile.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('placementPortalApp.studentProfile.studentID')}
                id="student-profile-studentID"
                name="studentID"
                data-cy="studentID"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.studentProfile.personalEmail')}
                id="student-profile-personalEmail"
                name="personalEmail"
                data-cy="personalEmail"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 600, message: translate('entity.validation.maxlength', { max: 600 }) },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.studentProfile.address')}
                id="student-profile-address"
                name="address"
                data-cy="address"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 600, message: translate('entity.validation.maxlength', { max: 600 }) },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.studentProfile.contactNumber')}
                id="student-profile-contactNumber"
                name="contactNumber"
                data-cy="contactNumber"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.studentProfile.linkedinProfile')}
                id="student-profile-linkedinProfile"
                name="linkedinProfile"
                data-cy="linkedinProfile"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 600, message: translate('entity.validation.maxlength', { max: 600 }) },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.studentProfile.dob')}
                id="student-profile-dob"
                name="dob"
                data-cy="dob"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.studentProfile.location')}
                id="student-profile-location"
                name="location"
                data-cy="location"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 600, message: translate('entity.validation.maxlength', { max: 600 }) },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.studentProfile.githubProfile')}
                id="student-profile-githubProfile"
                name="githubProfile"
                data-cy="githubProfile"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 600, message: translate('entity.validation.maxlength', { max: 600 }) },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.studentProfile.cgpa')}
                id="student-profile-cgpa"
                name="cgpa"
                data-cy="cgpa"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.studentProfile.noOfBacklogs')}
                id="student-profile-noOfBacklogs"
                name="noOfBacklogs"
                data-cy="noOfBacklogs"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              {/* <ValidatedField*/}
              {/*  id="student-profile-user"*/}
              {/*  name="user"*/}
              {/*  data-cy="user"*/}
              {/*  label={translate('placementPortalApp.studentProfile.user')}*/}
              {/*  type="select"*/}
              {/* >*/}
              {/*  <option value="" key="0" />*/}
              {/*  {users*/}
              {/*    ? users.map(otherEntity => (*/}
              {/*      <option value={otherEntity.id} key={otherEntity.id}>*/}
              {/*        {otherEntity.login}*/}
              {/*      </option>*/}
              {/*    ))*/}
              {/*    : null}*/}
              {/* </ValidatedField>*/}
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/student-profile" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button
                color="primary"
                id="save-entity"
                data-cy="entityCreateSaveButton"
                type="submit"
                onClick={e => {
                  console.log('HAHAHAHAH clicked');
                }}
              >
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

export default studentProfileUpdate;

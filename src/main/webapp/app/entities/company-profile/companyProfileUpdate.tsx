import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset, createProfile } from './company-profile.reducer';
import { ICompanyProfile } from 'app/shared/model/company-profile.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const companyProfileUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const companyProfileEntity = useAppSelector(state => state.companyProfile.entity);
  const loading = useAppSelector(state => state.companyProfile.loading);
  const updating = useAppSelector(state => state.companyProfile.updating);
  const updateSuccess = useAppSelector(state => state.companyProfile.updateSuccess);
  const handleClose = () => {
    props.history.push('/company-profile' + props.location.search);
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
    //   ...companyProfileEntity,
    //   ...values,
    //   user: users.find(it => it.id.toString() === values.user.toString()),
    // };

    const entity = {
      ...values,
    };

    if (isNew) {
      dispatch(createProfile(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...companyProfileEntity,
          user: companyProfileEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="placementPortalApp.companyProfile.home.createOrEditLabel" data-cy="CompanyProfileCreateUpdateHeading">
            <Translate contentKey="placementPortalApp.companyProfile.home.createOrEditLabel">Create or edit a CompanyProfile</Translate>
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
                  id="company-profile-id"
                  label={translate('placementPortalApp.companyProfile.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('placementPortalApp.companyProfile.email')}
                id="company-profile-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 600, message: translate('entity.validation.maxlength', { max: 600 }) },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.companyProfile.address')}
                id="company-profile-address"
                name="address"
                data-cy="address"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 600, message: translate('entity.validation.maxlength', { max: 600 }) },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.companyProfile.contactNumber')}
                id="company-profile-contactNumber"
                name="contactNumber"
                data-cy="contactNumber"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.companyProfile.linkedinProfile')}
                id="company-profile-linkedinProfile"
                name="linkedinProfile"
                data-cy="linkedinProfile"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 600, message: translate('entity.validation.maxlength', { max: 600 }) },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.companyProfile.location')}
                id="company-profile-location"
                name="location"
                data-cy="location"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 600, message: translate('entity.validation.maxlength', { max: 600 }) },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.companyProfile.technology')}
                id="company-profile-technology"
                name="technology"
                data-cy="technology"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.companyProfile.ctc')}
                id="company-profile-ctc"
                name="ctc"
                data-cy="ctc"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.companyProfile.overview')}
                id="company-profile-overview"
                name="overview"
                data-cy="overview"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 600, message: translate('entity.validation.maxlength', { max: 600 }) },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.companyProfile.bond')}
                id="company-profile-bond"
                name="bond"
                data-cy="bond"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.companyProfile.minimumCriteria')}
                id="company-profile-minimumCriteria"
                name="minimumCriteria"
                data-cy="minimumCriteria"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 600, message: translate('entity.validation.maxlength', { max: 600 }) },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.companyProfile.type')}
                id="company-profile-type"
                name="type"
                data-cy="type"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('placementPortalApp.companyProfile.noOfOpenings')}
                id="company-profile-noOfOpenings"
                name="noOfOpenings"
                data-cy="noOfOpenings"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              {/* <ValidatedField*/}
              {/*  id="company-profile-user"*/}
              {/*  name="user"*/}
              {/*  data-cy="user"*/}
              {/*  label={translate('placementPortalApp.companyProfile.user')}*/}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/company-profile" replace color="info">
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

export default companyProfileUpdate;

import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './company-profile.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CompanyProfileDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const companyProfileEntity = useAppSelector(state => state.companyProfile.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="companyProfileDetailsHeading">
          <Translate contentKey="placementPortalApp.companyProfile.detail.title">CompanyProfile</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="placementPortalApp.companyProfile.id">Id</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.id}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="placementPortalApp.companyProfile.email">Email</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.email}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="placementPortalApp.companyProfile.address">Address</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.address}</dd>
          <dt>
            <span id="contactNumber">
              <Translate contentKey="placementPortalApp.companyProfile.contactNumber">Contact Number</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.contactNumber}</dd>
          <dt>
            <span id="linkedinProfile">
              <Translate contentKey="placementPortalApp.companyProfile.linkedinProfile">Linkedin Profile</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.linkedinProfile}</dd>
          <dt>
            <span id="location">
              <Translate contentKey="placementPortalApp.companyProfile.location">Location</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.location}</dd>
          <dt>
            <span id="technology">
              <Translate contentKey="placementPortalApp.companyProfile.technology">Technology</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.technology}</dd>
          <dt>
            <span id="ctc">
              <Translate contentKey="placementPortalApp.companyProfile.ctc">Ctc</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.ctc}</dd>
          <dt>
            <span id="overview">
              <Translate contentKey="placementPortalApp.companyProfile.overview">Overview</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.overview}</dd>
          <dt>
            <span id="bond">
              <Translate contentKey="placementPortalApp.companyProfile.bond">Bond</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.bond}</dd>
          <dt>
            <span id="minimumCriteria">
              <Translate contentKey="placementPortalApp.companyProfile.minimumCriteria">Minimum Criteria</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.minimumCriteria}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="placementPortalApp.companyProfile.type">Type</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.type}</dd>
          <dt>
            <span id="noOfOpenings">
              <Translate contentKey="placementPortalApp.companyProfile.noOfOpenings">No Of Openings</Translate>
            </span>
          </dt>
          <dd>{companyProfileEntity.noOfOpenings}</dd>
          <dt>
            <Translate contentKey="placementPortalApp.companyProfile.user">User</Translate>
          </dt>
          <dd>{companyProfileEntity.user ? companyProfileEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/company-profile" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company-profile/${companyProfileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompanyProfileDetail;

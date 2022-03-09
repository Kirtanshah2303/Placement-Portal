import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './student-company-status.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StudentCompanyStatusDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const studentCompanyStatusEntity = useAppSelector(state => state.studentCompanyStatus.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="studentCompanyStatusDetailsHeading">
          <Translate contentKey="placementPortalApp.studentCompanyStatus.detail.title">StudentCompanyStatus</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="placementPortalApp.studentCompanyStatus.id">Id</Translate>
            </span>
          </dt>
          <dd>{studentCompanyStatusEntity.id}</dd>
          <dt>
            <span id="ctc">
              <Translate contentKey="placementPortalApp.studentCompanyStatus.ctc">Ctc</Translate>
            </span>
          </dt>
          <dd>{studentCompanyStatusEntity.ctc}</dd>
          <dt>
            <span id="location">
              <Translate contentKey="placementPortalApp.studentCompanyStatus.location">Location</Translate>
            </span>
          </dt>
          <dd>{studentCompanyStatusEntity.location}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="placementPortalApp.studentCompanyStatus.status">Status</Translate>
            </span>
          </dt>
          <dd>{studentCompanyStatusEntity.status}</dd>
          <dt>
            <Translate contentKey="placementPortalApp.studentCompanyStatus.student">Student</Translate>
          </dt>
          <dd>{studentCompanyStatusEntity.student ? studentCompanyStatusEntity.student.studentID : ''}</dd>
          <dt>
            <Translate contentKey="placementPortalApp.studentCompanyStatus.company">Company</Translate>
          </dt>
          <dd>{studentCompanyStatusEntity.company ? studentCompanyStatusEntity.company.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/student-company-status" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/student-company-status/${studentCompanyStatusEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StudentCompanyStatusDetail;

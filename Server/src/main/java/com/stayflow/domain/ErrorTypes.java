package com.stayflow.domain;

import graphql.ErrorClassification;

public enum ErrorTypes implements ErrorClassification{
  INVALID_CREDENTIALS,
  BAD_REQUEST,
  NOT_FOUND,
}

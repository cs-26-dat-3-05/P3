import { Link } from "react-router";
import styled from "styled-components";

export const BackLink = styled(Link)`
  display: inline-block;
  font-size: 12px;
  color: var(--primary-color);
  text-decoration: none;
  margin-bottom: 24px;
`;

export const PageHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
`;

export const CaseMeta = styled.p`
  margin: 0;
  font-size: 11px;
  color: #999;
  letter-spacing: 1px;
`;

export const CaseTitle = styled.h1`
  margin: 4px 0 0;
  font-size: 32px;
`;

export const EditButton = styled.button.attrs({ className: "box" })`
  && {
    background: var(--background-color);
    padding: 12px 20px;
    font-size: 12px;
    font-family: inherit;
    cursor: pointer;
  }
`;

export const Tabs = styled.div`
  display: flex;
  gap: 32px;
  border-bottom: 1px solid #ccc;
  margin: 24px 0;
`;

export const Tab = styled.button<{ $active: boolean }>`
  background: none;
  border: none;
  padding: 8px 0;
  cursor: pointer;
  font-family: inherit;
  font-size: 13px;
  color: var(--primary-color);

  ${({ $active }) =>
    $active &&
    `
        font-weight: bold;
        border-bottom: 3px solid var(--primary-color);
        margin-bottom: -1px;
    `}
`;

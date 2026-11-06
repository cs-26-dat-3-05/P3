import styled from "styled-components";

export const Container = styled.div<{ $columns: string }>`
  display: grid;
  grid-template-columns: ${(p) => p.$columns};
  gap: 16px;
  width: 100%;
`;

export const Card = styled.div.attrs({ className: "box" })`
  && {
    display: flex;
    flex-direction: column;
    min-width: 0;
    padding: 0;
  }
`;

export const CardHeader = styled.div`
  background-color: #e4e1d9;
  padding: 14px;
  text-transform: uppercase;
  font-size: 14px;
  font-weight: bold;
  border-bottom: 1px solid #bcbcbc;
`;

export const CardTable = styled.table`
  flex: 1;
  width: 100%;
  table-layout: fixed;
  border-collapse: collapse;

  td {
    padding: 14px;
    font-size: 14px;
    border-bottom: 2px dashed #dcdcdc;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  tr:last-child td {
    border-bottom: none;
  }
`;
export const TdRight = styled.td`
  text-align: right;
  font-weight: bold;
`;

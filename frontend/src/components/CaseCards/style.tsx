import styled from "styled-components";

interface TableCardProps {
  $width?: string;
}

export const TableCard = styled.table.attrs({
  className: "box",
})<TableCardProps>`
  flex-grow: 0;
  flex-shrink: 0;
  flex-basis: ${(props) => `calc(${props.width} -8 px)`};

  border: 1px solid #bcbcbc;
  border-collapse: collapse;

  td {
    padding: 8px;
    font-size: 14px;
    border-bottom: 1px dashed #dcdcdc;

    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  tr:last-child td {
    border-bottom: none;
  }

  th {
    background-color: #e4e1d9;
    padding: 8px;
    text-align: left;
    text-transform: uppercase;
    font-size: 14px;
    border-bottom: 1px solid #bcbcbc;
    column-span: all;
  }
`;

export const Container = styled.div`
  display: flex;
  gap: 16px;
`;
